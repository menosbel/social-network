export class ConfigController {
    private config;

    constructor(config) {
        this.config = config;
    }

    process(request, response) {
        response.setHeader("Content-Type", "text/javascript");
        response.writeHead(200);
        response.end(this.getResponseContent());
    }

    private getResponseContent(): string {
        const configJson = JSON.stringify(this.config.public);
        return `window.APP_CONFIG = ${configJson};`;
    }
}
