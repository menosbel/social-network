export async function expectThrows(func: () => void, ErrorClass) {
    await expect(func()).rejects.toThrowError(ErrorClass);
}
