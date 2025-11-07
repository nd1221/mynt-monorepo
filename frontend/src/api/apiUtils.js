export function handleError(err) {
    if (err.response) {
        console.log(err.response.data);
        console.log(err.response.status);
        console.log(err.response.headers);
    } else {
        console.log(`Error: ${err.message}`);
    }
}