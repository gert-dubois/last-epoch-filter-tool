window.copyToClipboard = function (content) {
    if (navigator.clipboard && window.isSecureContext) {
        return navigator.clipboard.writeText(content);
    }
    return new Promise((res, rej) => {
        let container = document.createElement("textarea");
        container.textContent = content;
        container.style.position = "absolute";
        container.style.opacity = "0";
        document.body.appendChild(container);
        container.select();
        document.execCommand('copy') ? res() : rej();
        container.remove();
    });
};