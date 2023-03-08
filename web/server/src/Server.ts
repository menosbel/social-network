import express from 'express';
import helmet from 'helmet';
import morgan from 'morgan';
import compression from 'compression';
import cookieParser from 'cookie-parser';
import { ConfigController } from './controllers/ConfigController';

export class Server {
    private readonly express;
    private readonly config;
    private inner: any;

    constructor(config) {
        this.express = express();
        this.config = config;
        this.registerSigInt();
        this.registerMiddlewares();
        this.registerRoutes();
    }

    start() {
        const port = this.config.get('port');
        this.inner = this.express.listen(port, () => {
            if (this.config.get('logging')) {
                console.log(`Server listening on port ${port}`);
            }
        });
    }

    stop() {
        this.inner.close();
    }

    private registerSigInt() {
        process.on('SIGINT', function () {
            console.log('\nGracefully shutting down from SIGINT (Ctrl-C)');
            process.exit(1);
        });
    }

    private registerMiddlewares() {
        this.express.use(helmet({ contentSecurityPolicy: false }));
        this.express.use(compression());
        this.express.use(cookieParser());
        if (this.config.get('logging')) {
            this.express.use(morgan('tiny'));
        }
    }

    private registerRoutes() {
        this.express.use(express.static(process.cwd() + '/public'));
        this.express.get('/config', (req, res) => new ConfigController(this.config).process(req, res));
        this.express.get('/*', (req, res) => { res.sendFile(process.cwd() + '/public/index.html'); });
    }
}
