((typeof period !== 'undefined') && ((typeof objectId == 'undefined' ) &&
(start = period.begin,
end = period.end,
start = start.split(" "),
end = end.split(" "),
start = start[0].split("."),
end = end[0].split("."),
start = new Date(start[2], start[1], start[0]),
end = new Date(end[2], end[1], end[0]),
days_count = Math.round((end - start) / (1000 * 60 * 60 * 24)),
days_count < 31)) || (typeof objectId !== 'undefined'));
