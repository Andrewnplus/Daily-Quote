const TOKEN_KEY = "jwtToken"

window.createAuthorizationTokenHeader = function () {
    const token = getJwtToken();
    if (token) {
        return {"Authorization": "Bearer " + token};
    } else {
        return {};
    }
};

window.getJwtToken = function () {
    return localStorage.getItem(TOKEN_KEY);
}