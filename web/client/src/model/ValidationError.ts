import { CustomError } from 'ts-custom-error';

export class ValidationError extends CustomError {
    constructor(public property: string, message: string) {
        super(message);
    }
}
