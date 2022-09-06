import {
    connectStreamSource,
    disconnectStreamSource
} from "https://unpkg.com/@hotwired/turbo@7.1.0/dist/turbo.es2017-esm.js";
import "https://cdn.jsdelivr.net/npm/@stomp/stompjs@6.1.2/bundles/stomp.umd.min.js";

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
            this.webSocketClient.deactivate();
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
        // StompJS Docs: https://stomp-js.github.io/guide/stompjs/using-stompjs-v5.html

        const protocol = location.protocol === "https:" ? "wss" : "ws";
        const url = protocol + "://" + document.location.host + "/stomp";
        const webSocketClient = new StompJs.Client({
            brokerURL: url,
            connectHeaders: {},
            debug: str => {
                if (this.debug === "true") {
                    console.log(str);
                }
            },
            reconnectDelay: 200, // wait 200 ms before attempting to auto reconnect
            heartbeatIncoming: 0, // client does not want to receive heartbeats from the server
            heartbeatOutgoing: 0 // client will not send any heartbeats
        });

        webSocketClient.onDisconnect = frame => {
            if (this.debug === "true") {
                console.log("STOMP client has been disconnected.")
            }
        };

        webSocketClient.onWebSocketClose = frame => {
            if (this.debug === "true") {
                console.log("STOMP connection has been closed.")
            }
        };

        webSocketClient.onConnect = frame => {
            if (this.debug === "true") {
                console.log("STOMP client has been connected.")
            }

            webSocketClient.subscribe(this.src, message => {
                // called when the client receives a STOMP message from the server
                if (message.body) {
                    this.dispatchMessageEvent(message.body)
                }
            });
        };

        webSocketClient.onStompError = function (frame) {
            // Will be invoked in case of error encountered at Broker
            // Bad login/passcode typically will cause an error
            // Complaint brokers will set `message` header with a brief message. Body may contain details.
            // Compliant brokers will terminate the connection after any error
            console.error('Broker reported error: ' + frame.headers['message']);
            console.error('Additional details: ' + frame.body);
        };

        webSocketClient.activate();

        return webSocketClient;
    }
}

customElements.define("turbo-stream-connection", TurboStreamConnection);
