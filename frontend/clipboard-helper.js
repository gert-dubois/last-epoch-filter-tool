window.copyToClipboard = function (content) {
    let container = document.createElement("textarea");
    container.textContent = content;
    container.style.position = "fixed";
    document.body.appendChild(container);
    return navigator.clipboard.writeText(container.textContent)
        .then(() => document.body.removeChild(container));
};