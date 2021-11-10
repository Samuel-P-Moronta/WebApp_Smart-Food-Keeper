import websocket
import _thread
from datetime import datetime, time
import time
import json, random
from threading import Thread


#++++++++++++++++++++++++++++++++++++++
#                                      #
# SMART FOOD KEEPER-FINAL PROJECT BY:  #
# SAMUEL P. MORONTA | YEHUDY DE PEÑA   #
#                                      #
#++++++++++++++++++++++++++++++++++++++#

def on_message(ws, message):
    print(message)

def on_error(ws, error):
    print(error)

def on_close(ws, close_status_code, close_msg):
    print("### closed ###")

def generate_container_data():
    """
    This functions is to receive data from load cell
    and adjust the values to send it to the server
    in java with the endpoint [server/container]
    """
    maxWeight = 5
    # Insert function to get weight data here and comment [wight_data in]
    ###################################################
    weight_data_in = round(random.uniform(0.00,5.00),2)

    weight = 0
    status_code = 0
    # To keep correct values
    if weight_data_in > 0:
        weight = weight_data_in

        if weight <= maxWeight/3:
            # Minimun level in container
            status_code = 1
        elif weight > maxWeight/3 and weight <= (maxWeight/3)*2:
            # Middle level in container
            status_code = 2
        else:
            # Full level in container
            status_code = 3
    elif weight_data_in == 0:
        status_code = 1

    else:
        # If we have {-0.00} data so that means to calibrate sensor
        # wrong data received
        return "Calibrar el sensor"

    # To send sensor data as JSON format
    container_data = {'weight':weight,'statusCode':status_code}

    # To encode JSON for sending data to java server so server can
    # understand data sent
    json_sensor_data = json.dumps(container_data,indent=4,default=str)

    return json_sensor_data

def generate_data():
    """
    This is same {to generate container data} with the  difference
    that this function is about to receive data from shelf and then
    send to the server in java with the endpoint [server/shelf]
    """
    random.seed(datetime.now())
    temperature = round(random.uniform(22.00,31.00),2)
    humidity= round(random.uniform(40.00,70.00),2)
    generated_date = datetime.now()
    deviceName = ['SH001']
    fruitType = ['PINEAPPLE','PAPAYA']
    percentage_overripe = 25
    percentage_ripe = 25
    percentage_unripe = 50
    fruit_cant = 4

    random_sensor_data = {'temperature': temperature,'humidity':humidity,'fruitCant':fruit_cant,
                          'fruitType':fruitType[1],'percentageOverripe':percentage_overripe,
                          'percentageRipe':percentage_ripe,'percentageUnripe':percentage_unripe,'shelf':deviceName[0]}

    json_sensor_data = json.dumps(random_sensor_data,indent=4,default=str)


    return json_sensor_data


def send_sensor_data(ws):

    def run(*args):
        i = 0
        while True:
            print("#################################")
            print("[*] Sending sensor data # [{}] ".format(i))
            print("#################################")

            data = generate_data()
            time.sleep(5)
            ws.send(data)
            print("+++++++++++++++++++++++++++++++++++")
            i = i + 1
        #time.sleep(1)
        ws.close()
        print("thread terminating...")
    _thread.start_new_thread(run, ())

def send_container_data(ws):

    def run(*args):
        i = 0
        while True:
            print("##################################")
            print("[*] Sending data # [{}] to container ".format(i))
            print("##################################")
            data = generate_container_data()
            time.sleep(10)
            ws.send(data)
            print("+++++++++++++++++++++++++++++++++++")
            i = i + 1
        #time.sleep(1)
        ws.close()
        print("thread terminating...")
    _thread.start_new_thread(run, ())

def connect_websocket_shelf():
    """
    Function to connect websocket shelf endponit /server/shelf
    """

    websocket.enableTrace(True)
    # Change localhost to your current ip localhost example: 10.0.0.10
    ws = websocket.WebSocketApp("ws://10.0.0.8:7000/server/shelf",
                                on_open=send_sensor_data,
                                on_message=on_message,
                                on_error=on_error,
                                on_close=on_close)
    ws.run_forever()

def connect_websocket_container():
    """
    Function to connect websocket container endponit /server/container
    """

    websocket.enableTrace(True)
    # Change localhost to your current ip localhost example: 10.0.0.10
    ws = websocket.WebSocketApp("ws://10.0.0.8:7000/server/container",
                                on_open=send_container_data,
                                on_message=on_message,
                                on_error=on_error,
                                on_close=on_close)

    ws.run_forever()


if __name__ == "__main__":
    "Threading to keep twice function running"
    Thread(target=connect_websocket_shelf).start()
    Thread(target=connect_websocket_container).start()

