const { authorize } = require('./oAuthgenerator');
const { google } = require("googleapis");
const { isValid } = require('./isValid');
const { deleteEntry } = require('./deleteEntry');

function getGoogleSheet() {
    const spreadsheetId = "1JhcPZyreWkm44T5LnfNvojUBCK6YDtDnufJ-Em0UjcY"; //試算表 id
    const range = "表單回應 1!A1:O300"

    async function readSheet(auth) {
        const sheets = google.sheets({
            version: "v4",
            auth
        });
        const sheetOfRes = await sheets.spreadsheets.values.get({
            spreadsheetId: spreadsheetId,
            range: range
        });

        const sheetOfResData = sheetOfRes.data.values;


        for (let i = 300; i >= 1; i--) {
            let resObj = { "A": 0, "B": 0, "C": 0 };
            if (!sheetOfResData[i]) {
                continue;
            }
            //假設對第一個報告者的評價是從表單的 column D 開始，最後一欄其他建議算成績沒有要用
            for (let j = 3; j < 15; j++) {
                let res = sheetOfResData[i][j];
                if (res) {
                    resObj[res] += 1;  // 確保 res 存在於 resObj 中
                }
            }

            //刪除不合法的
            if (!isValid(resObj)) {
                deleteEntry(i, 15, spreadsheetId, auth);
            }
            else {
                //重複回覆者只留最後一項
                for (let rowIndex = 0; rowIndex < i; rowIndex++) {
                    if (sheetOfResData[i][2] == sheetOfResData[rowIndex][2]) {
                        //學號一樣的
                        deleteEntry(rowIndex, 15, spreadsheetId, auth);
                    }
                }
            }
        }



    }
    authorize().then(readSheet).catch(console.error);

}

module.exports = { getGoogleSheet };
//getGoogleSheet();

