let btnConnect;
let btnDisconnect;
let btnSend;

let request;
let response;

let ws = null;

window.onload = () => {
  btnConnect = document.getElementById("btnConnect");
  btnDisconnect = document.getElementById("btnDisconnect");
  btnSend = document.getElementById("btnSend");

  request = document.getElementById("request");
  response = document.getElementById("response");

  btnConnect.addEventListener("click", WebSocketTest.onConnect);
  btnDisconnect.addEventListener("click", WebSocketTest.onDisconnect);
  btnSend.addEventListener("click", WebSocketTest.onSend);
};

class UIRender {

  static renderRequest = data => {
    request.innerHTML +=(`<div>request: ${data}</div>`);
  };

  static renderResponse = data => {
    response.innerHTML +=(`<div>response: ${data}</div>`);
  };
}

class WebSocketTest {

  static sockWs = null;

  static onConnect = () => {
    this.sockWs = new WebSocket("ws://localhost:8080/webSocket");

    this.sockWs.onopen = (e) => {
      console.log("Соединение установленно");
    };

    this.sockWs.onerror = (e) => {
      console.error(`Произошла ошибка при подключении`);
    };

    this.sockWs.onclose = (e) => {
      if (e.wasClean) {
        console.log(`Соединение закрыто; eventCode: ${e.code}; eventMessage: ${e.reason}`);
      }
    };

    this.sockWs.onmessage = (e) => {
      console.log(`onmessage, data: ${e.data};`);
      UIRender.renderResponse(e.data);
    }
  };

  static onDisconnect = () => {
    this.sockWs.close();
  };

  static onSend = () => {
    this.sockWs.send("testsddd");
    UIRender.renderRequest("testsddd");
  };
}

