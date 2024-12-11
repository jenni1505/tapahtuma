document.addEventListener("DOMContentLoaded", function () {
    const calendar = document.getElementById("calendar");
    const monthYear = document.getElementById("monthYear");
    const prevMonthButton = document.getElementById("prevMonth");
    const nextMonthButton = document.getElementById("nextMonth");

    let currentDate = new Date();

    function renderCalendar(date) {
        const year = date.getFullYear();
        const month = date.getMonth();

        // Päivämäärätiedot
        const firstDayOfMonth = (new Date(year, month, 1).getDay() + 6) % 7; // Kuukauden ensimmäinen viikonpäivä (maanantai aloituspäiväksi)
        const lastDateOfMonth = new Date(year, month + 1, 0).getDate(); // Kuukauden viimeinen päivä
        const lastDateOfPrevMonth = new Date(year, month, 0).getDate(); // Edellisen kuukauden viimeinen päivä

        // Päivitä kuukausi ja vuosi
        monthYear.textContent = date.toLocaleDateString("fi-FI", { month: "long", year: "numeric" });

        // Luo taulukon otsikko
        let calendarHTML = "<table>";
        calendarHTML += "<tr><th>Ma</th><th>Ti</th><th>Ke</th><th>To</th><th>Pe</th><th>La</th><th>Su</th></tr>";
        calendarHTML += "<tr>";

        // Lisää tyhjät solut edellisen kuukauden päiville
        for (let i = 0; i < firstDayOfMonth; i++) {
            calendarHTML += `<td class="empty"></td>`;
        }

        // Lisää kuukauden päivät
        for (let day = 1; day <= lastDateOfMonth; day++) {
            const dateStr = `${year}-${String(month + 1).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
            const isToday = new Date().toDateString() === new Date(year, month, day).toDateString();
            calendarHTML += `<td class="${isToday ? "today" : ""}" data-date="${dateStr}">
                        ${day}
                     </td>`;
            if ((day + firstDayOfMonth) % 7 === 0) {
                calendarHTML += "</tr><tr>";
            }
        }


        // Lisää tyhjät solut seuraavan kuukauden päiville
        const remainingDays = (7 - ((lastDateOfMonth + firstDayOfMonth) % 7)) % 7;
        for (let i = 1; i <= remainingDays; i++) {
            calendarHTML += `<td class="empty"></td>`;
        }

        calendarHTML += "</tr>";
        calendarHTML += "</table>";

        calendar.innerHTML = calendarHTML;
    }

    // Vaihda kuukautta
    prevMonthButton.addEventListener("click", function () {
        currentDate.setMonth(currentDate.getMonth() - 1);
        renderCalendar(currentDate);
    });

    nextMonthButton.addEventListener("click", function () {
        currentDate.setMonth(currentDate.getMonth() + 1);
        renderCalendar(currentDate);
    });

    // Alusta kalenteri
    renderCalendar(currentDate);
});