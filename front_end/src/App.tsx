import './App.css';
import '@mantine/core/styles.css';
import { MantineProvider } from '@mantine/core';
import { App } from "./Router.tsx";
import { BrowserRouter as Router } from "react-router-dom";

function AppWrapper() {
  return (
    <MantineProvider theme={{}}>
      <div
        style={{ fontSize: '28px', fontWeight: 'bold' }}>
        {"WeekX 針對在職班同學報告評分"}

      </div>
      <Router>
        <App />
      </Router>

    </MantineProvider>

  )
}

export default AppWrapper;
