import { anything, capture, deepEqual, mock, verify, when } from 'ts-mockito';
import { MethodToStub } from 'ts-mockito/lib/MethodToStub';
import { MethodStubVerificator } from 'ts-mockito/lib/MethodStubVerificator';
import { StrictEqualMatcher } from 'ts-mockito/lib/matcher/type/StrictEqualMatcher';
import { DeepEqualMatcher } from 'ts-mockito/lib/matcher/type/DeepEqualMatcher';
import { MethodStubSetter } from 'ts-mockito/lib/MethodStubSetter';

export function verifySequence(methods: Array<any>) {
    for (let i = 0; i < methods.length; i++) {
        if (i !== methods.length - 1) {
            verify(methods[i]).calledBefore(methods[i + 1]);
        } else {
            verify(methods[i]).called();
        }
    }
}

export function verifyArgs<T0 = any, T1 = any, T2 = any, T3 = any, T4 = any, T5 = any>(method: ((a0: T0, a1: T1, a2: T2, a3: T3, a4: T4, a5: T5) => any)|MethodToStub): [T0, T1, T2, T3, T4, T5] {
    let methodStub: MethodToStub = method as MethodToStub;
    if (typeof method === 'function') {
        methodStub = method(anything(), anything(), anything(), anything(), anything(), anything());
    }
    verify(methodStub).called();
    const actions = methodStub.mocker.getActionsByName(method.name);
    const methodReference = methodStub.mocker.mock[actions[0].methodName];
    return capture<T0, T1, T2, T3, T4, T5>(methodReference).last();
}

export function verifyArg<T>(method: ((a: T) => any)|MethodToStub): T {
    let methodStub = method;
    if (typeof method === 'function') {
        methodStub = method(anything());
    }
    return verifyArgs<T>(methodStub)[0];
}

export function eq<T>(expectedValue: T): T {
    return deepEqual(expectedValue);
}

function forceDeepEquals<T>(method: T) {
    const methodStub = (method as unknown) as MethodToStub;
    methodStub.matchers = methodStub.matchers.map(m => {
        if (m instanceof StrictEqualMatcher) {
            const matcher = new DeepEqualMatcher((m as any).expectedValue);
            matcher.toString = function (): string {
                const expected = (this as any).expectedValue;
                return JSON.stringify(expected);
            };
            return matcher;
        } else {
            return m;
        }
    });
}

export function whenEq<T>(method: T): MethodStubSetter<T> {
    forceDeepEquals(method);
    return when(method);
}

export function verifyEq<T>(method: T): MethodStubVerificator<T> {
    forceDeepEquals(method);
    return verify(method);
}

export function mockEq<T>(clazz?: any): T {
    const mockedObj = mock<T>(clazz);
    return new Proxy(mockedObj, {
        get: (target: any, name: PropertyKey) => {
            const returnValue = target[name];
            if (returnValue instanceof Function) {
                return (...args: Array<any>) => {
                    const methodStub = returnValue(...args);
                    forceDeepEquals(methodStub);
                    return methodStub;
                };
            }

            return returnValue;
        },
    });
}
