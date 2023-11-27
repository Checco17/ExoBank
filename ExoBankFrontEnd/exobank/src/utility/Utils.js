const { DateTime } = require("luxon");

export function formattaData(data) {
  return DateTime.fromISO(data).toFormat("dd LLLL yyyy");
}

