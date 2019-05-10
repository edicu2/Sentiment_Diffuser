var mqtt;
var reconnectTimeout = 2000;
var host="172.26.1.15";
var port=1884;

function onConnect(){
    console.log("Connected");
    var message = new Paho.MQTT.Message("4조 화이팅");
    message.destinationName ="/demd";
    mqtt.send(message);
}
function MQTTconnect(){
    console.log(host+" "+port);
    mqtt = new Paho.MQTT.Client(host,port,"hello");
    console.log(mqtt)
    var options = {
        timeout: 3,
        onSuccess:onConnect,
        userName:'hyeonbin', 
        password:'password',
        onFailure:console.log('Failed')
        
    };
    console.log(options)
    mqtt.connect(options);
}