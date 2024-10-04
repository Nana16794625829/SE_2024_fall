const { google } = require("googleapis");
function deleteEntry(invalidRow, columnRange, spreadsheetId, auth) {
    let newLetter = String.fromCharCode("A".charCodeAt(0) + columnRange);
    let rangeToDelete = "A" + invalidRow + ":" + newLetter + invalidRow;

    const sheet = google.sheets({ version: "v4", auth });


    sheet.spreadsheets.values.clear({
        spreadsheetId: spreadsheetId,
        range: rangeToDelete      // 要清除的範圍，例如 A7:M7
    }, (err, res) => {
        if (err) {
            console.error("The API returned an error: " + err);
            return;
        }
        console.log(`Range ${rangeToDelete} cleared successfully.`);
    });


}
//要改
//好像只能清資料，不能刪整行
module.exports = { deleteEntry };