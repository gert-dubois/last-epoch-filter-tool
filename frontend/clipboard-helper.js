window.copyToClipboard = function (content) {
    if (navigator.clipboard && window.isSecureContext) {
        return navigator.clipboard.writeText(content);
    } else {
        let container = document.createElement("textarea");
        container.textContent = content;
        container.style.position = "absolute";
        container.style.opacity = "0";
        document.body.appendChild(container);
        return new Promise((res, rej) => {
            document.execCommand('copy') ? res() : rej();
        }).finally(() => container.remove());
    }
};