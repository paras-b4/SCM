console.log("script loading");

let currentTheme = getTheme();
console.log(currentTheme);
changeTheme();

function changeTheme() {
    document.querySelector("html").classList.add(currentTheme);

    const changeThemeButton = document.querySelector("#theme_change_button");
    
    if (changeThemeButton) {
        changeThemeButton.addEventListener("click", (event) => {
            const oldTheme = currentTheme;
            console.log("change theme button clicked");

            if (currentTheme === "dark") {
                currentTheme = "light";
            } else {
                currentTheme = "dark";
            }

            setTheme(currentTheme);
            document.querySelector("html").classList.remove(oldTheme);
            document.querySelector("html").classList.add(currentTheme);
            changeThemeButton.querySelector("span").textContent=currentTheme;
        });
    }
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem('theme');
    return theme ? theme : "light";
}
