var app = (function () {

    class Point{
        constructor(x,y){
            this.x=x;
            this.y=y;
        }        
    }
    
    var stompClient = null;

    var addPointToCanvas = function (point) {        
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.beginPath();
        ctx.arc(point.x, point.y, 3, 0, 2 * Math.PI);
        ctx.stroke();
    };
    
    
    var getMousePosition = function (evt) {
        canvas = document.getElementById("canvas");
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    };


    var connectAndSubscribe = function (id) {
            console.info('Connecting to WS...');
            var socket = new SockJS('/stompendpoint');
            stompClient = Stomp.over(socket);

            //subscribe to /topic/TOPICXX when connections succeed
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/newpoint.'+id, function (eventbody) {
                    var point =JSON.parse(eventbody.body);
                    var canvas = document.getElementById("canvas");
                    var ctx = canvas.getContext("2d");
                    ctx.beginPath();
                    ctx.arc(point.x, point.y, 1, 0, 2 * Math.PI);
                    ctx.stroke();
                });
                stompClient.subscribe('/topic/newpolygon.'+id, function (eventbody) {
                    _clearCanvas();
                    var myCanvas = document.getElementById("canvas");
                    var ctx = myCanvas.getContext("2d");
                    var puntos =JSON.parse(eventbody.body);
                    ctx.beginPath();
                    puntos.map(function (point, index) {
                    if(index == 0){
                        ctx.moveTo(point.x,point.y);
                        first = point;
                    }
                    else{
                        if(puntos.length == index){
                            ctx.lineTo(first.x,first.y)
                        }
                        else if(index % 4 == 0){
                            ctx.moveTo(point.x,point.y);
                        }
                        else{
                            ctx.lineTo(point.x,point.y);
                        }
                    }
                    ctx.stroke();
                    })
                    ctx.closePath();
                    ctx.fill();
                });
            });
        };
    
    

    return {

        init: function () {
            var can = document.getElementById("canvas");
            
            //websocket connection
            connectAndSubscribe();
        },

        publishPoint: function(px,py){
            var pt=new Point(px,py);
            console.info("publishing point at "+pt);
            addPointToCanvas(pt);

            //publicar el evento
        },

        connect:function(id){
            connectAndSubscribe(id);
        }

        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }
    };

})();