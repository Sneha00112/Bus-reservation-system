import socket
import threading

def handle_client(client_sock):
    unix_sock = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
    try:
        unix_sock.connect("/tmp/mysql.sock")
    except Exception as e:
        print("Failed to connect to unix socket")
        client_sock.close()
        return

    def forward(src, dst):
        try:
            while True:
                data = src.recv(4096)
                if not data:
                    break
                dst.sendall(data)
        except:
            pass
        finally:
            src.close()
            dst.close()

    threading.Thread(target=forward, args=(client_sock, unix_sock)).start()
    threading.Thread(target=forward, args=(unix_sock, client_sock)).start()

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server.bind(('127.0.0.1', 3306))
server.listen(5)
print("Proxy listening on 3306")
while True:
    client_sock, addr = server.accept()
    handle_client(client_sock)
