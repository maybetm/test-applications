let btnConnect;
let btnDisconnect;
let btnSend;

let request;
let response;

window.onload = () => {
  btnConnect = document.getElementById("btnConnect");
  btnDisconnect = document.getElementById("btnDisconnect");
  btnSend = document.getElementById("btnSend");

  request = document.getElementById("request");
  response = document.getElementById("response");

  const appMain = new AppMain(`ws://${window.location.host}/myHandler`);

  btnConnect.addEventListener("click", appMain.doConnect);
  btnDisconnect.addEventListener("click", appMain.doDisconnect);
  btnSend.addEventListener("click", appMain.doSend);
};

class AppMain {

  #socketProcessor = null;
  #url = null;

  #onOpen = () => console.log("Соединение установленно");
  #onclose = (e) => console.log(`Соединение закрыто; eventCode: ${e.code}; eventMessage: ${e.reason}`);
  #onMessage = (data) => response.innerHTML += (`<div>response: ${data}</div>`);
  #onError = () => console.error(`Произошла ошибка при подключении`);

  constructor(url)
  {
    this.#url = url;
    this.#socketProcessor = new WebSocketProcessor(this.#url, this.#onOpen,
                                                   this.#onMessage, this.#onclose, this.#onError)
  }

  doConnect = () => this.#socketProcessor.doConnect();
  doDisconnect = () => this.#socketProcessor.doDisconnect();
  doSend = () => {
    const data = JSON.stringify({id: 123, message: "testMessage"});
    this.#socketProcessor.doSend(data);
    request.innerHTML += (`<div>request: ${data}</div>`);
  }
}
