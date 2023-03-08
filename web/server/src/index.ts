import path from 'path';
import { Server } from './Server';
require('dotenv').config({ path: path.resolve(path.join(process.cwd(), '../'), '.env') });
const config = require('config');

const server = new Server(config);
server.start();
