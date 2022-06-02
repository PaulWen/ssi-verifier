import {
    connectStreamSource,
    disconnectStreamSource
} from "https://unpkg.com/@hotwired/turbo@7.1.0/dist/turbo.es2017-esm.js";
import "https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js";

class TurboStreamConnection extends HTMLElement {
    get src() {
        return this.getAttribute("src");
    }

    get debug() {
        return this.getAttribute("debug");
    }

    set src(value) {
        if (value) {
            this.setAttribute("src", value);
        } else {
            this.removeAttribute("src");
        }
    }

    connectedCallback() {
        connectStreamSource(this);
        this.webSocketClient = this.connectWebSocket();
    }

    disconnectedCallback() {
        disconnectStreamSource(this);
        if (this.webSocketClient) {
            this.webSocketClient.disconnect();
            this.webSocketClient = null;
        }
    }

    /**
     * Called in response to a websocket message. Unpacks the websocket message
     * and dispatches it as a new MessageEvent to Turbo Streams.
     *
     * @param data The data to dispatch
     */
    dispatchMessageEvent(data) {
        const event = new MessageEvent("message", {data});
        this.dispatchEvent(event);
    }

    connectWebSocket() {
        var url = "ws://" + document.location.host + "/stomp";
        var webSocketClient = Stomp.client(url);

        var headers = {};
        webSocketClient.connect(headers, () => {
            webSocketClient.subscribe(this.src, message => {
                // called when the client receives a STOMP message from the server
                if (message.body) {
                    this.dispatchMessageEvent(message.body)
                }
            });
        });

        if (this.debug === "false") {
            webSocketClient.debug = (str) => {
            };
        }

        return webSocketClient;
    }
}

customElements.define("turbo-stream-connection", TurboStreamConnection);
