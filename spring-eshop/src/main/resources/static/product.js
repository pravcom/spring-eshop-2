let stompProduct = null;
let stompBucket = null;

//подключаемся к серверу по окончании загрузки таблицы
window.onload = function () {
    connectProduct()
    connectBucket()
}

function connectProduct() {
    console.log('product');
    let socket = new SockJS('/socket');
    stompProduct = Stomp.over(socket);
    stompProduct.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompProduct.subscribe('/topic/products', function (product) {
            renderItemProduct(product);
        });
    });
}
//отправка сообщения на сервер
$(function (){
  var myButton = document.getElementById("add-product")
  myButton.addEventListener("click",function (event){
      event.preventDefault()
      sendContent()
  })
})
function sendContent(){

    stompProduct.send("/app/products",{},JSON.stringify({
        'title': $("#title").val(),
        "price": $("#price").val()
    }));
}
function renderItemProduct(productJson) {
    let product = JSON.parse(productJson.body);
    var button = document.createElement("button");
    button.textContent = "Add to bucket";
// Устанавливаем атрибут onclick для вызова функции sendIdProduct(this)
    button.setAttribute("onclick", "sendIdProduct(this)");

    var table = document.getElementById("table");
    var row = document.createElement("tr");

    var cellId = document.createElement("td")
    cellId.textContent=product.id
    var cellTitle = document.createElement("td")
    cellTitle.textContent=product.title
    var cellPrice = document.createElement("td")
    cellPrice.textContent=product.price
    var cellBtn = document.createElement("td")

    cellBtn.appendChild(button)

    row.appendChild(cellId)
    row.appendChild(cellTitle)
    row.appendChild(cellPrice)
    row.appendChild(cellBtn)

    table.appendChild(row)

}
function connectBucket() {
    console.log('socket');
    let socket = new SockJS('/socket');
    stompBucket = Stomp.over(socket);

    stompBucket.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompBucket.subscribe('/topic/bucket', function(bucket) {
            renderItemBucket(bucket);
        });
    });
}
//отправка сообщения на сервер
function sendIdProduct(button) {
    var row = button.closest("tr");
    var id = row.querySelector("td:nth-child(1)").textContent.trim();

    stompBucket.send("/app/bucket", {}, JSON.stringify({
        'id': id
    }));
}
function renderItemBucket(bucketJSON){
    console.log('Получаем ссылку на таблицу');
    var bucket = JSON.parse(bucketJSON.body);
    var bucketSum = document.getElementById("bucket-sum")
    var sum = bucket.sum.toString()
    bucketSum.innerText = sum
}
