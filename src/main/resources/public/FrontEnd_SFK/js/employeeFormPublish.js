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


function addForm() {
    /*
     <th>Sucursal</th>
    <th>Fruta</th>
    <th>Cantidad</th>
    <th>% Descuento (Unitario)</th>
    <th>Fecha de inspeccion</th>
    -->

     */
    var branchOffice = "";
    var fruitType = "";
    var fruitQuantity = "";
    var discountPercentage = ""
    branchOffice = $("#brachOffice").val();
    fruitType = $("#fruitType").val();
    fruitQuantity = $("#fruitQuantity").val();
    discountPercentage = $("#discountPercentage").val()
    console.log("Sucursal: ",branchOffice);
    console.log("Tipo de fruta: ", fruitType);
    console.log("Porciento de descuento", discountPercentage)



    if (branchOffice != "" && fruitType != "" && fruitQuantity != "") {

        var dbActiva = dataBase.result; //Nos retorna una referencia al IDBDatabase.

        // Para realizar una operación de agreagr, actualización o borrar.
        // Es necesario abrir una transacción e indicar un modo: readonly, readwrite y versionchange
        var transaccion = dbActiva.transaction(["form"], "readwrite");

        //Manejando los errores.
        transaccion.onerror = function (e) {
            alert(request.error.name + '\n\n' + request.error.message);
        };

        transaccion.oncomplete = function (e) {
            document.querySelector("#branchOffice").value = '';
            alert('Formulario agregado correctamente!');
            listarDatos();
            ///window.location.reload();
        };

        //Abriendo la colección de datos que estaremos usando.
        var formulario = transaccion.objectStore("form");

        //Para agregar se puede usar add o put; el add requiere que no exista el objeto.
        var request = formulario.put({
            //id: document.querySelector("#id").value,
            branchOffice: document.querySelector("#brachOffice").value,
            fruitType: document.querySelector("#fruitType").value,
            quantity: document.querySelector("#fruitQuantity").value,
            discountPercentage: document.querySelector("#discountPercentage").value,


            //user: document.querySelector("#idUsuario").value,
        });

        request.onerror = function (e) {
            var mensaje = "Error: " + e.target.errorCode;
            console.error(mensaje);
            alert(mensaje)
        };

        request.onsuccess = function (e) {
            console.log("Datos Procesado con exito");
            document.querySelector("#brachOffice").value = "";
            document.querySelector("#fruitType").value = "";
            document.querySelector("#fruitQuantity").value = "";
            document.querySelector("#discountPercentage").value = "";
            console.log(formulario)
        };
    }
}

function listarDatos() {
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

            //Función que permite seguir recorriendo el cursor.
            cursor.continue();
        } else {
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
        fila += "<td>" + lista_formulario[key].branchOffice + "</td>"
        fila += "<td>" + lista_formulario[key].fruitType + "</td>"
        fila += "<td>" + lista_formulario[key].quantity + "</td>"
        fila += "<td>" + lista_formulario[key].discountPercentage + "</td>"
        fila += "</tr>"
    }
    document.getElementById("formList").innerHTML = fila;
}

