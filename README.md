# WebApp_Smart-Food-Keeper

## By Samuel Peña Moronta & Yehudy De Peña Rodríguez

Smart Food Keeper is a project focused on supermarkets that seeks to reduce fruit waste caused by the selectivity carried out by the businesses when selecting the products to be for sale with image recognition. However, there are other technologies that make up more modules of the project that play an important role in the main objective. 

The system offers temperature and humidity monitoring, as well as tracking of the kilograms of fruit that have been wasted using sensors, raspberry pi, esp32 that communicate via Wi-Fi.

The system has a web application for its administration and registration of users, companies, and branches. In addition to this, it also features views for tracking fruit wastage, the amount of fruit on the shelves with their respective ripeness levels, and temperature and humidity levels. It also consists of a express service for exchanging fruits that are at risk of being wasted, so that they are consumed instead of ending up in the trash. 

The system has demonstrated an average effectiveness of 98.98%, detecting pineapples and papayas in their unripe, ripe, and overripe stages. With these levels of effectiveness, the project has proven to be an effective application for the detection of maturity. It also demonstrates the ability of artificial intelligence to recognize fruits at different stages of ripeness, which can be used in many innovative solutions for the future. 

## Tools and programming languages used throughout the project.
1) Web app. 

  Java, Javascript, Javalin (Microframework), Thymeleaf, H2 Data base (Local) , Rest Api, Websockets, ORM, Hibernate, Javax Mail, Bootstrap, Cloud: Deployed Azure Virtual Machine
  
2) Machine Learning. 
  
  Python, Tensorflow, Goolge Colaboratory (Trainning), Yolov4 Darknet, Dataset (12,000 pictures)
  
  Trainning repository:
  
  https://github.com/Samuel-P-Moronta/Ripeness_Fruit_Detection_Classification-SFK
----------------------------------------------------------------------

Raspberry Pi 3B, Esp32, DHT22.

## Project Diagram 

![image](https://user-images.githubusercontent.com/55027470/164112652-caccc324-253a-454b-90ba-477fa261aa5f.png)


## Views Web App

Here you can find the number of branches enabled with our project, as well as the number of shelves and containers
![Dashboard](https://user-images.githubusercontent.com/55027470/164113094-81983039-e9a1-48cb-acba-790d5ba1ac94.PNG)

![image](https://user-images.githubusercontent.com/55027470/164116493-b7b6d941-231f-4fe7-93c1-c432fd56a11e.png)


Also, 

![Dashboard_2](https://user-images.githubusercontent.com/55027470/164114424-7facf83d-cf91-4fa9-a8a4-35c6b298109f.PNG)

Shelf moniroting
![NewShelfMonitoring](https://user-images.githubusercontent.com/55027470/164117100-5ad54377-d8c9-4fcc-941e-b2bb67d2bf9a.PNG)


Notifications for: Overripe fruit detected, Fruit Supply, Temperature, Humidiy. 
![NotificationCheck](https://user-images.githubusercontent.com/55027470/164114931-9d51a31c-d6f9-4f16-8fbb-eda07f292044.PNG)


![EmployeeEmail](https://user-images.githubusercontent.com/55027470/164115613-3bd8ee76-37fd-4b4e-b77e-72fec411e0d9.PNG)


When the system detects that there are fruits in a overripe state, the employee makes the decision whether to waste it or sell (Express) it with a discount percentage previously established by the supermarket admin.

![OverripeCheck](https://user-images.githubusercontent.com/55027470/164115157-0e6c6915-e110-45fb-8ebd-d26b2835626a.PNG)



When the employee finishes the inspection and has decided that the fruits are going to be sold, a notification is sent to the clients subscribed in the platform
![image](https://user-images.githubusercontent.com/55027470/164115955-f9d95251-f9a7-4af7-a7a7-81ed38dbb3cd.png)


![ClientMail](https://user-images.githubusercontent.com/55027470/164115550-928b0e54-73f6-4319-9ab7-e65da53d4033.PNG)


Employee Register and Edit 

![EmployeeRegister](https://user-images.githubusercontent.com/55027470/164114623-7709b7c0-e6d2-4f7d-8f5d-2515ef595ed9.PNG)
![EmployeeList](https://user-images.githubusercontent.com/55027470/164114635-b7db40fb-f6d9-4c29-ab05-79df1ed8262e.PNG)
![EmployeeRegister](https://user-images.githubusercontent.com/55027470/164114644-b19cf371-c66d-4267-abdb-b980d7b40ae6.PNG)


Others views:


![RootPortal](https://user-images.githubusercontent.com/55027470/164115803-ced745ec-3b0d-4ced-bfe4-f2ab9786b759.PNG)

![RootPortalAddShelf](https://user-images.githubusercontent.com/55027470/164115811-7cbbf61f-9c3d-4bd4-b92c-5a32e1919a96.PNG)



Templates used: 

https://github.com/Samuel-P-Moronta/ConnectPlusAdmin-Free-Bootstrap-Admin-Template

https://www.bootstrapdash.com/product/simple-landing-page/







