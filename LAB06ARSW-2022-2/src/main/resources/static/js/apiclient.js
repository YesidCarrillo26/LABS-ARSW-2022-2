const url = 'http://localhost:8080/blueprints/';

var apiclient =(function(){
    //DATO IMPORTANTE
    //PARA QUE FUNCIONE EN LA INTERFAZ
    //SE DEBE HACER DOBLE CLICK EN GETBLUEPRINTS
    var dataCallBack = [];
    return{
        /**
         * Funcion generada para retornar los datos del autor de acuerdo con
         * su nombre. La busqueda se hará mediante peticiones HTTP (GET) 
         * hacia el API REST el cual contiene planos ya creados
         * @param {String} authname 
         * @param {Function} callback 
         * @returns 
         */
        getBlueprintsByAuthor : function(authname,callback){
            $.get( url+authname, function( data ) {
                dataCallBack = data;
              });
              console.log("DATA DADA EN GET1 DE APICLIENT")
              console.log(dataCallBack);
            return callback(dataCallBack);
        },
        /**
         * Funcion generada para retornar el plano que requiere el usuario
         * de acuerdo con el autor y el nombre del plano pedido. Esta petición
         * se hará mediante peticiones por AJAX. Este usa una nomenclatura
         * JSON donde pide la URL donde va a salir la información y la etiqueta
         * SUCCESS es el retorno de la información. 
         * @param {String} authname 
         * @param {String} bpname 
         * @param {Function} callback 
         */
        getBlueprintsByNameAndAuthor: function(authname,bpname,callback){
            jQuery.ajax({
                url: url+authname+"/"+bpname,
                //LA ASINCRONIA SE DESACTIVA DEBIDO A QUE 
                //PRIMERO COGE TODA LA LISTA, LO CUAL NOS GENERA UN ERROR
                async: false,
                success: function(data){
                    callback(data);
                },
                contentType: "application/json"
            })
        },

        /**
         * 
         * @param {*} authname 
         * @param {*} bpname 
         * @param {*} callback 
         * @param {*} points
         */
        updateBlueprint:function(authname,bpname,points,callback){
            $.ajax({
                url: url+authname+"/"+bpname,
                type: 'PUT',
                data: points,
                success: function(data){
                    callback(data);
                }
            })
        },
        deletePrint:function(author,bpname,data){
            console.log("ENTRA A DELETEPRINT DE APICLIENT")
            $.ajax({
                url: url+author+"/"+bpname,
                type: 'DELETE',
                data:data,
                contentType: "application/json"
            })
        }
    }
})();