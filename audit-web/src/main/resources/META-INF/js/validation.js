var result = false;
var days_count_local = typeof days_count === 'undefined' || days_count === null ? 31 : days_count;
if (typeof period !== 'undefined' && period !== null && days_count_local <= 31) {
    result = true;
}
return result;
