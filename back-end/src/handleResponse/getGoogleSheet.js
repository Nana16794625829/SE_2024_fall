const { authorize } = require('./oAuthgenerator');
const { google } = require("googleapis");
const { isValid } = require('./isValid');
const { deleteEntry } = require('./deleteEntry');

function getGoogleSheet() {
    const spreadsheetId = "107-yh150J0wUmyZdZA84LVquXC469fu8eCW6utUvdZE"; //試算表 id
    const range = "表單回應 1!A1:O300"//API規定這個範圍要寫死，寫大一點沒有影響，只要該範圍有涵蓋到所有同學回覆即可
    let startCol = 3;//對第一位報告者的回應位於 sheet 的哪一行，假設是 D
    let endCol = 12;//對最後一位報告者的回應位於 sheet 的哪一行，假設是 M
    let studentIDCol = 1;//學號在哪行

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


        for (let i = 300; i > 1; i--) {
            let resObj = { "A": 0, "B": 0, "C": 0 };
            if (!sheetOfResData[i]) {
                continue;
            }
            //假設對第一個報告者的評價是從表單的 column D 開始，最後一欄其他建議算成績沒有要用
            for (let j = startCol; j <= endCol; j++) {
                let res = sheetOfResData[i][j];
                if (res) {
                    resObj[res] += 1;  // 確保 res 存在於 resObj 中
                }
            }

            //刪除不合法的
            if (!isValid(resObj)) {
                //API 取得的試算表 index 從 0 開始，但 google 試算表本身從 1 開始，+ 他才能定位到正確的列
                deleteEntry(i + 1, endCol, spreadsheetId, auth);
            }
            else {
                //重複回覆者只留最後一項
                for (let rowIndex = 0; rowIndex < i; rowIndex++) {
                    if (sheetOfResData[i][studentIDCol] == sheetOfResData[rowIndex][studentIDCol]) {
                        //學號一樣的
                        deleteEntry(rowIndex + 1, endCol, spreadsheetId, auth);
                    }
                }
            }
        }



    }
    authorize().then(readSheet).catch(console.error);

}

module.exports = { getGoogleSheet };
getGoogleSheet();

