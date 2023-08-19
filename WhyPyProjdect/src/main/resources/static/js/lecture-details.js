document.addEventListener("DOMContentLoaded", function (){SwitchCategoryName();});


function SwitchCategoryName()
{
    const categoryCells = document.querySelectorAll(".category");
    categoryCells.forEach(cell => {
        const categoryText = cell.textContent;
        if (categoryText == "1") {
            cell.textContent = "강의1";
        } else if (categoryText == "2") {
            cell.textContent = "강의2";
        }
    });
}