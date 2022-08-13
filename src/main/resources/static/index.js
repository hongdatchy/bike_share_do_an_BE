async function fetchGet(url, token = null, needResponse = true) {
    const response = await fetch(url, {
        method: 'GET',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json',
            'token' : token,
            'Accept': 'application/json'
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
    });
    if(needResponse){
        return response.json();
    }
}

async function fetchPost(url, data, token = null) {
    const response = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json',
            'token' : token
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
        body: JSON.stringify(data)
    });
    return response.json();
}

async function fetchDelete(url, token = null) {
    const response = await fetch(url, {
        method: 'DELETE',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json',
            'token' : token
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
    });
    return response.json();
}

async function loadAllBikeInfo(){
    // return await fetchGet("http://localhost:8080/api/common/findAllBikeInfoForDeMo")
    return await fetchGet("http://155.248.164.224:8181/api/common/findAllBikeInfoForDeMo")
}


let listBikeInfo

function render(){
    let listBikeInfoTrTag = listBikeInfo.map((bikeInfo, index)=>{
        return `<tr class="bike_info">
                    <td>${bikeInfo.id}</td>
                    <td>${bikeInfo.deviceId}</td>
                    <td>${bikeInfo.frameNumber}</td>
                    <td>${bikeInfo.productYear}</td>
                    <td>${bikeInfo.battery}</td>
                    <td><div class="qrcode" style="width:100px; height:100px;"></div></td>
                    <td class="status_lock">${bikeInfo.statusLock ? `<img src="padlock_open.png" alt=""/>`: `<img src="padlock_lock.png" alt=""/>`}</td>
                    <td><button type="button" class="btn btn-primary" onclick="onClickPushLatLngByMQTTButton(${bikeInfo.id})">Đẩy toạ độ</button></td>
                    <td><button type="button" class="btn btn-primary" onclick="onClickCloseLock(${bikeInfo.id}, ${index})">Đóng khoá</button></td>
                </tr>`
    })
    listBikeInfoTrTag.unshift(`<tr>
                    <td>bike id</td>
                    <td>device id</td>
                    <td>frame number</td>
                    <td>product year</td>
                    <td>battery</td>
                    <td>QR code</td>
                    <td>Status Lock</td>
                    <td>Đẩy toạ độ</td>
                    <td>Đóng khoá</td>
                </tr>`)
    document.getElementById("list_bike_info").innerHTML = listBikeInfoTrTag.join('')
    createQR()

}

loadAllBikeInfo().then(rs => {
    listBikeInfo = rs
    render()
}).then(() => {
    connectSocket()
})

function onClickPushLatLngByMQTTButton(bikeId){
    fetchGet("http://localhost:8080/api/common/pushLatLngToServerByMqtt/" + bikeId, "", false).then(r => {})
}

function onClickCloseLock(bikeId, index){
    listBikeInfo[index].statusLock = false;
    render()
    fetchGet("http://localhost:8080/api/common/closeLockToServerByMqtt/" + bikeId, "", false).then(r => {})
}



function createQR(){
    let listTag = document.getElementsByClassName("qrcode")

    for(let i = 0; i< listTag.length; i++){
        let qrcode = new QRCode(listTag[i], {
            width : 100,
            height : 100
        });
        makeCode(qrcode, listBikeInfo[i].id.toString())
    }
}


function makeCode (qrcode, bikeId) {
    qrcode.makeCode(bikeId);
}

let stompClient = null;
function connectSocket() {

    let socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function() {
        console.log('Web Socket is connected');
        stompClient.subscribe("/topic/messages/" , function(message) {
            let messageBody = message.body
            let bikeId = messageBody.split(",")[0]
            let mes = messageBody.split(",")[1]

            if(mes == "op" || mes == "op continue"){
                getTag(bikeId).innerHTML =
                    `<img src="padlock_open.png" alt=""/>`
            } else if(mes == "cl"){
                getTag(bikeId).innerHTML =
                    `<img src="padlock_lock.png" alt=""/>`
            }
        });
    });
}

function getTag(bikeId){
    for(let i = 0; i< listBikeInfo.length; i++){
        if(listBikeInfo[i].id == bikeId){
            return document.getElementsByClassName("status_lock")[i]}
    }
}


