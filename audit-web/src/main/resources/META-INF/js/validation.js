if(typeof period !== 'undefined') {
    var start = period.begin;
    var end = period.end;
    start = start.split(" ");
    end = end.split(" ");
    start = start[0].split(".");
    end = end[0].split(".");
    start = new Date(start[2], start[1], start[0]);
    end = new Date(end[2], end[1], end[0]);
    var days_count = Math.round((end - start) / (1000 * 60 * 60 * 24));
}

return ((typeof period !== 'undefined') && ((typeof objectId === 'undefined') && (days_count < 31)) ||
 (typeof objectId !== 'undefined')) || ((typeof period === 'undefined') && (typeof objectId === 'undefined'));
