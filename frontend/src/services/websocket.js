import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client/dist/sockjs';

const WS_URL = import.meta.env.VITE_WS_URL || '/ws';

let stompClient = null;
let subscriptions = {};

export function connectWebSocket(token, onConnected, onError) {
  if (stompClient?.connected) {
    onConnected?.();
    return;
  }

  stompClient = new Client({
    webSocketFactory: () => new SockJS(WS_URL),
    connectHeaders: { Authorization: `Bearer ${token}` },
    reconnectDelay: 3000,
    heartbeatIncoming: 10000,
    heartbeatOutgoing: 10000,
    onConnect: () => {
      console.log('[WS] Connected');
      onConnected?.();
    },
    onStompError: (frame) => {
      console.error('[WS] STOMP error:', frame);
      onError?.(frame);
    },
    onDisconnect: () => {
      console.log('[WS] Disconnected');
    },
  });

  stompClient.activate();
}

export function disconnectWebSocket() {
  Object.values(subscriptions).forEach((sub) => sub.unsubscribe());
  subscriptions = {};
  if (stompClient) {
    stompClient.deactivate();
    stompClient = null;
  }
}

export function subscribeToOperations(docId, callback) {
  const key = `ops-${docId}`;
  if (subscriptions[key]) subscriptions[key].unsubscribe();

  subscriptions[key] = stompClient.subscribe(
    `/topic/document/${docId}/operations`,
    (message) => {
      const operation = JSON.parse(message.body);
      callback(operation);
    }
  );
  return () => {
    subscriptions[key]?.unsubscribe();
    delete subscriptions[key];
  };
}

export function subscribeToCursors(docId, callback) {
  const key = `cursor-${docId}`;
  if (subscriptions[key]) subscriptions[key].unsubscribe();

  subscriptions[key] = stompClient.subscribe(
    `/topic/document/${docId}/cursors`,
    (message) => {
      const cursor = JSON.parse(message.body);
      callback(cursor);
    }
  );
  return () => {
    subscriptions[key]?.unsubscribe();
    delete subscriptions[key];
  };
}

export function subscribeToPresence(roomId, callback) {
  const key = `presence-${roomId}`;
  if (subscriptions[key]) subscriptions[key].unsubscribe();

  subscriptions[key] = stompClient.subscribe(
    `/topic/room/${roomId}/presence`,
    (message) => {
      const presence = JSON.parse(message.body);
      callback(presence);
    }
  );
  return () => {
    subscriptions[key]?.unsubscribe();
    delete subscriptions[key];
  };
}

export function sendOperation(docId, operation) {
  if (!stompClient?.connected) return;
  stompClient.publish({
    destination: `/app/document/${docId}/operation`,
    body: JSON.stringify(operation),
  });
}

export function sendCursor(docId, cursorData) {
  if (!stompClient?.connected) return;
  stompClient.publish({
    destination: `/app/document/${docId}/cursor`,
    body: JSON.stringify(cursorData),
  });
}

export function sendJoinRoom(roomId, userData) {
  if (!stompClient?.connected) return;
  stompClient.publish({
    destination: `/app/room/${roomId}/join`,
    body: JSON.stringify(userData),
  });
}

export function sendLeaveRoom(roomId, userData) {
  if (!stompClient?.connected) return;
  stompClient.publish({
    destination: `/app/room/${roomId}/leave`,
    body: JSON.stringify(userData),
  });
}

export function isConnected() {
  return stompClient?.connected || false;
}
