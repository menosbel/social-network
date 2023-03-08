export class Config {
    static get<T = any>(name: string): T {
        // @ts-ignore
        return window.APP_CONFIG[name];
    }
}
