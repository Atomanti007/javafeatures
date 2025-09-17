const http = require("http");

const server = http.createServer((req, res) => {
    // 5 másodperc késleltetés
    setTimeout(() => {
        res.writeHead(200, { "Content-Type": "text/plain" });
        res.end("OK\n");
    }, 5000);
});

const PORT = 8080;
server.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}/`);
});
