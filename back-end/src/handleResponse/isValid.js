function isValid(resObj) {
    if (resObj.A < 1 || resObj.A > 2) return false
    //else if (resObj.B < 4 || resObj.B > 7) return false
    else if (resObj.C < 1 || resObj.C > 2) return false

    return true
}

module.exports = { isValid };