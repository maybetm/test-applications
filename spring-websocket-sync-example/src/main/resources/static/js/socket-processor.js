class WebSocketProcessor {

  #url = null;
  #webSocket = null;
  #onOpen = null;
  #onMessage = null;
  #onClose = null;
  #onError = null;

  constructor(url, onOpen, onMessage, onClose, onError)
  {
    this.#url = url;
    this.#onOpen = onOpen;
    this.#onMessage = onMessage;
    this.#onClose = onClose;
    this.#onError = onError;
  }

  doConnect = () => {
    this.#webSocket = new WebSocket(this.#url);
    this.#webSocket.onopen = e => this.#onOpen();
    this.#webSocket.onclose = e => this.#onClose(e);
    this.#webSocket.onmessage = e => this.#onMessage(e.data);
    this.#webSocket.onerror = e => this.#onError;
  };

  doDisconnect = () => {
    this.#webSocket.close();
  };

  doSend = (data) => {
    this.#webSocket.send(data);
  };
}
