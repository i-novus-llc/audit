if (typeof period === 'undefined' || period === null
        || typeof days_count === 'undefined' || days_count === null || days_count <= 31) {
    return true;
}
if (days_count > 31 && typeof objectId !== 'undefined' && objectId !== null && objectId !== '') {
    return true;
}
return false;
