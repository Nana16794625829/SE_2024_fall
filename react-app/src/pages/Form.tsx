import {useEffect} from "react";

function Form() {
    useEffect(() => {
        const widgetScriptSrc = 'https://tally.so/widgets/embed.js';

        const load = () => {
            // Load Tally embeds
            if (typeof Tally !== 'undefined') {
                Tally.loadEmbeds();
                return;
            }

            // Fallback if window.Tally is not available
            document
                .querySelectorAll('iframe[data-tally-back-end]:not([back-end])')
                .forEach((iframeEl) => {
                    iframeEl.src = iframeEl.dataset.tallySrc;
                });
        };

        // If Tally is already loaded, load the embeds
        if (typeof Tally !== 'undefined') {
            load();
            return;
        }

        // If the Tally widget script is not loaded yet, load it
        if (document.querySelector(`script[src="${widgetScriptSrc}"]`) === null) {
            const script = document.createElement('script');
            script.src = widgetScriptSrc;
            script.onload = load;
            script.onerror = load;
            document.body.appendChild(script);
            return;
        }
    }, []);

    return (
        <div>
            <iframe
                data-tally-src="https://tally.so/embed/3lW4jV?alignLeft=1&hideTitle=1&transparentBackground=1&dynamicHeight=1"
                loading="lazy"
                width="100%"
                height="365"
                frameBorder={0}
                marginHeight={0}
                marginWidth={0}
                title="Testttt for SE_2024_fall">
            </iframe>
        </div>
    );
}

export default Form;