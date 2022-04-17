//Dependiendo el navegador se busca la referencia del objeto.
const indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB

//Se indica el nombre y la versión
const dataBase = indexedDB.open("sfkproject", 1);
//Se ejecuta la primera vez que se crea la estructura o se cambia la versión de la base de datos.
dataBase.onupgradeneeded = function (e) {
    console.log("Creando la estructura de la tabla");

    //obteniendo la conexión activa
    active = dataBase.result;

    //creando la colección:
    //En este caso, la colección, tendrá un Id autogenerado.
    const formulario = active.createObjectStore("form", {keyPath: 'id', autoIncrement: true});

    formulario.createIndex('por_indice', 'indice', {unique: true});

};
//El evento que se dispara una vez, lo
dataBase.onsuccess = function (e) {
    listarDatos();
    console.log('Proceso ejecutado de forma correctamente');
};
dataBase.onerror = function (e) {
    console.error('Error en el proceso: ' + e.target.errorCode);
};

$(document).ready(function () {

    // code to read selected table row cell data (values).
    $("#myTableNotification").on('click', '.btn btn-success', function () {
        // get the current row
        var currentRow = $(this).closest("tr");

        var col1 = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
        var col2 = currentRow.find("td:eq(1)").text(); // get current row 2nd TD
        var col3 = currentRow.find("td:eq(2)").text(); // get current row 3rd TD
        var data = col1 + "\n" + col2 + "\n" + col3;

        alert(data);
    });
});
function findForms() {

    $("#myTableNotification").on('click', '.btn-success', function () {
        // get the current row
        var currentRow = $(this).closest("tr");

        var col1 = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
        var col2 = currentRow.find("td:eq(1)").text(); // get current row 2nd TD
        var col3 = currentRow.find("td:eq(2)").text(); // get current row 3rd TD
        var data = col1 + "\n" + col2 + "\n" + col3;

        //alert(data);

        let serverResponse = "";
        let endpoint = "display-forms"
        let idNotificationSelect = col1;
        document.getElementById("idNotificationSelect").value = idNotificationSelect
        const Http = new XMLHttpRequest();
        const url = location.protocol + "//" + location.hostname + ':' + location.port + '/api/' + endpoint + "/" + idNotificationSelect;
        Http.open("GET", url);
        Http.send();
        let i = 0;
        Http.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                serverResponse = JSON.parse(Http.responseText);
                console.log(serverResponse)
                if (serverResponse != null) {
                    var fruitType = serverResponse.fruitType;
                    var shelfId = serverResponse.shelfId;
                    var branchOffice = serverResponse.branchOffice;
                    var discountPercentage = serverResponse.discountPercentage;
                    var overripeCant = serverResponse.overripeCant;
                    document.getElementById("shelfId").value = shelfId;
                    document.getElementById("discountPercentage").value = discountPercentage;
                    document.getElementById("overripeCant").value = overripeCant;
                    var inspectionType = document.getElementById("inspectionType").value;
                    if (fruitType == "pineapple") {
                        document.getElementById("fruitType").value = "PI\u00D1A";
                    } else {
                        if (fruitType == "papaya") {
                            document.getElementById("fruitType").value = "LECHOSA";
                        }
                    }
                    console.log("Tipo de fruta: ", fruitType)
                    addForm(fruitType, shelfId, branchOffice, discountPercentage, overripeCant, inspectionType)
                    listarDatos();

                }
            }
        }
    });
}

/*
                console.log("Tipo de fruta: ",serverResponse.fruitType);
                for(let i = 0; i < serverResponse.branchOffice.length+1; i++){
                    console.log("BranchOffice: ", serverResponse.branchOffice[i].address.city)
                    for(let j = 0; j < serverResponse.fruitProduct.length+1; j++){
                        console.log("Fruit product: ", serverResponse.fruitProduct[j].fruitType)
                    }
                }
 */
function displayFormToRegister(branchOffice, fruitProduct) {
    var branchOffice = document.getElementById("idBranchOfficeEmp");
    var shelfSelect = document.getElementById("shelfSelect");
    var fruitType = document.getElementById("fruitType");
    var fruitCant = document.getElementById("fruitQuantity");
    var discountPercentage = document.getElementById("discountPercentage");

}

function loadBranchOffice(data) {
    var branchOffice = document.getElementById("idBranchOfficeEmp");


    let j = 0;
    for (let i in data) {
        console.log("Data: ", data['address.direction'])
        j = parseInt(i) + 1;
        branchOffice.options[j].text = data[i].address.direction;
    }
}

//addForm(fruitType,shelfId,branchOffice,discountPercentage,overripeCant);
function addForm(fruitType, shelfId, branchOffice, discountPercentage, overripeCant) {

    /*
     <th>Sucursal</th>
    <th>Fruta</th>
    <th>Cantidad</th>
    <th>% Descuento (Unitario)</th>
    <th>Fecha de inspeccion</th>
    -->

     */
    //var data = findMyBranchOffice();
    // console.log("Datos en la funcion findBranchOffice: ",data)

    //branchOffice = $("#idBranchOffice").val();
    var ipt = document.getElementById("inspectionType");
    var fruitQ = document.getElementById("fruitQuantity")
    var btnInspection = document.getElementById("addPost");
    btnInspection.disabled = false;
    ipt.disabled = false;
    fruitQ.disabled = false;
    shelfId = $("#shelfId").val();
    fruitType = $("#fruitQuantity").val();
    overripeCant = $("#overripeCant").val();
    discountPercentage = $("#discountPercentage").val();
    let idNotificationSelect = document.getElementById("idNotificationSelect")
    var fruitQuantity = $("#fruitQuantity").val()
    var inspectionType = $("#inspectionType").val()

    //console.log("Sucursal: ", branchOffice);
    console.log("Tipo de fruta: ", fruitType);
    console.log("Porciento de descuento", discountPercentage)
    console.log("Cantidad overripe: ", overripeCant)
    console.log("Tipo de inspeccion: ", inspectionType)
    console.log("Id notification: ", idNotificationSelect.value)


    if (shelfId != "" && branchOffice != "" && fruitType != "" && fruitQuantity != "" && inspectionType != "") {
        if (fruitQuantity > overripeCant) {
            alert("La cantidad de frutas a publicar no puede exceder la cantidad de frutas detectadas muy maduras")
        } else {
            var dbActiva = dataBase.result; //Nos retorna una referencia al IDBDatabase.

            // Para realizar una operación de agreagr, actualización o borrar.
            // Es necesario abrir una transacción e indicar un modo: readonly, readwrite y versionchange
            var transaccion = dbActiva.transaction(["form"], "readwrite");

            //Manejando los errores.
            transaccion.onerror = function (e) {
                alert(request.error.name + '\n\n' + request.error.message);
            };
            var overripeCant = document.querySelector("#overripeCant").value;
            var fruitQuantity = document.querySelector("#fruitQuantity").value;
            var ipt = document.getElementById("inspectionType");
            var fruitQ = document.getElementById("fruitQuantity")
            //var btnInspection = document.getElementById("addPost");
            var resta = overripeCant - fruitQuantity;

            transaccion.oncomplete = function (e) {
                //document.querySelector("#branchOffice").value = '';

                alert('Formulario agregado correctamente!');
                //document.querySelector("#overripeCant").value = - document.querySelector("#fruitQuantity").value
                //document.querySelector("#fruitQuantity").value = "";
                //document.querySelector("#overripeCant").value = resta;
                if (resta == 0) {
                    document.getElementById("inspectionTypePrueba").value = "Ya no quedan frutas que clasificar";
                    ipt.disabled = true;
                    fruitQ.disabled = true;
                    btnInspection.dsabled = true;
                } else {
                    ipt.disabled = false;
                    fruitQ.disabled = false;
                    btnInspection.disabled = false;
                }
                listarDatos();
                ///window.location.reload();
            };

            //Abriendo la colección de datos que estaremos usando.
            var formulario = transaccion.objectStore("form");

            //Para agregar se puede usar add o put; el add requiere que no exista el objeto.
            var request = formulario.put({
                //id: document.querySelector("#id").value,
                // branchOffice: document.querySelector("#brachOffice").value,
                idNotificationSelect: document.querySelector("#idNotificationSelect").value,
                shelfId: document.querySelector("#shelfId").value,
                fruitType: document.querySelector("#fruitType").value,
                discountPercentage: document.querySelector("#discountPercentage").value,
                quantity: document.querySelector("#fruitQuantity").value,
                inspectionType: document.querySelector("#inspectionType").value,
                //user: document.querySelector("#idUsuario").value,
            });


            request.onerror = function (e) {
                var mensaje = "Error: " + e.target.errorCode;
                console.error(mensaje);
                alert(mensaje)
            };

            request.onsuccess = function (e) {
                console.log("Datos Procesado con exito");
                //document.querySelector("#brachOffice").value = "";
                //document.querySelector("shelfId").value = "";
                //document.querySelector("#fruitType").value = "";
                document.querySelector("#fruitQuantity").value = "";
                //document.querySelector("#discountPercentage").value = "";
                console.log(formulario)

            };
        }
    }
}

function listarDatos() {
    let overripeCant = document.getElementById("overripeCant").value;
    var ipt = document.getElementById("inspectionType");
    var fruitQ = document.getElementById("fruitQuantity")
    let btnFormIndexDB = document.getElementById("addPost");
    let btnSendForm = document.getElementById("enviarForm");


    console.log("Valor cargado desde la BD 9091", overripeCant);

    var data = dataBase.result.transaction(["form"]);
    var formulario = data.objectStore("form");
    var contador = 0;
    var formulario_recuperados = [];

    //Abriendo el cursor.
    formulario.openCursor().onsuccess = function (e) {
        //Recuperando la posicion del cursor
        var cursor = e.target.result;
        if (cursor) {
            contador++;
            //recuperando el objeto.
            formulario_recuperados.push(cursor.value);
            //
            if (cursor.value.length > 0) {
                console.log("LENGHT DE LA TABLA")
            }

            console.log("Value cursor: ", cursor.value)
            let fruitQuantity = cursor.value.quantity;
            console.log("Valor cargado en indexDB", fruitQuantity);
            console.log("Overripe cant value: ", overripeCant);
            if (fruitQuantity > 0) {
                var restar = overripeCant - fruitQuantity;
                if (restar == 0) {
                    ipt.disabled = true;
                    fruitQ.disabled = true;
                } else {
                    ipt.disabled = false;
                    fruitQ.disabled = false;
                }
                console.log("valor restado: ", restar)
                if (restar < 0) {
                    console.log("Overrip para sumar: ", document.querySelector("#overripeCant").value)
                    //document.querySelector("#overripeCant").value = restar + overripeCant;
                    var oCant = document.querySelector("#overripeCant").value
                    console.log("Valor resultante: ", oCant);
                    document.querySelector("#overripeCant").value = oCant;
                }
                document.querySelector("#overripeCant").value = restar;


            }
            //Función que permite seguir recorriendo el cursor.
            cursor.continue();


        } else {
            var overripeC = document.getElementById("overripeCant").value;
            if (overripeC == 0) {
                btnFormIndexDB.disabled = true;
            } else {
                btnFormIndexDB.disabled = false;
            }
            if (contador == 0) {
                btnSendForm.disabled = true;
            } else {
                btnSendForm.disabled = false;
            }
            console.log("La cantidad de registros recuperados es: " + contador);
        }

    };
    //Una vez que se realiza la operación se llama la impresión.
    data.oncomplete = function () {
        imprimirTabla(formulario_recuperados);
    }
}

function imprimirTabla(lista_formulario) {
    // creando la tabla...
    var fila = ""
    for (var key in lista_formulario) {

        console.log("indice: ", lista_formulario[key])
        fila += "<tr>"
        fila += "<td>" + lista_formulario[key].shelfId + "</td>"
        fila += "<td>" + lista_formulario[key].fruitType + "</td>"
        fila += "<td>" + lista_formulario[key].quantity + "</td>"
        fila += "<td>" + lista_formulario[key].inspectionType + "</td>"

        /*
        fila += "<td>" + lista_formulario[key].discountPercentage + "</td>"

         */
        fila += "</tr>"
    }
    document.getElementById("formList").innerHTML = fila;
}

//abriendo el objeto para el websocket
var webSocket;
var tiempoReconectar = 4000;
$(document).ready(function () {
    conectar()
    //Endicar id Boton enviar datos
    $("#enviarForm").click(function () {
        console.log("Enviando formulario al servidor")
        sendDataToServer();
        //window.location.reload(true);
    });
    $("#inspectNotOverripe").click(function () {
        console.log("Cancelando inspeccion")
        sendInspectNotOverripe();
        //window.location.reload(true);
    });
    $("#cancelButton").click(function () {
        console.log("Cancelando inspeccion")
        reloadFromCancel();
        //window.location.reload(true);
    });
});
function sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < milliseconds);
}
function sendInspectNotOverripe() {
    //datos obtenido de forma correcta
    let idNotificationSelect = document.getElementById("idNotificationSelect").value;
    let inspectionNotOverripe = [{idNotificationSelect: idNotificationSelect,inspectionNotOverripe: true}];
    webSocket.send(JSON.stringify(inspectionNotOverripe));
    sleep(1500)
    window.location.reload(true);

}



function reloadFromCancel() {
    window.location.reload(true);
}

function sendDataToServer() {
    var data = dataBase.result.transaction(["form"]);
    var formularios = data.objectStore("form");
    var listaFormulario = formularios.getAll();

    listaFormulario.onsuccess = function () {

        //datos obtenido de forma correcta
        webSocket.send(JSON.stringify(listaFormulario.result));
        alert("form enviado de forma exitosa!")
        limpiarDB();
        listarDatos();
        window.location.reload(true);
    };
}

function conectar() {
    /*Cambiar a wss cuando se haga deploay para https*/
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/form-register");
}

function limpiarDB() {
    const data = dataBase.result.transaction(["form"], "readwrite");
    const objecStore = data.objectStore("form");
    const repuesta = objecStore.clear();
    repuesta.onsuccess = function () {
        //Se han borrados todos los formulario de la BD
        console.log("Se han borrados todos los formulario de la BD");
    };
}
function verificarConexion(){
    if(!webSocket || webSocket.readyState === 3){
        conectar();
    }
}
setInterval(verificarConexion, tiempoReconectar); //para reconectar.




//findForms();

