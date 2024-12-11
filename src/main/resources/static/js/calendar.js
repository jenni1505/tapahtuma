document.addEventListener("DOMContentLoaded", async function () {
    const calendar = document.getElementById("calendar");

    let events = [];

    // Hae tapahtumat palvelimelta
    async function fetchEvents() {
        try {
            const response = await fetch("/api/events");
            if (!response.ok) {
                throw new Error("Failed to fetch events");
            }
            events = await response.json();
            updateCalendar();
        } catch (error) {
            console.error("Error fetching events:", error);
        }
    }

    function updateCalendar() {
        const cells = calendar.querySelectorAll("td[data-date]");
        cells.forEach(cell => {
            const cellDate = cell.getAttribute("data-date");
            const eventForDate = events.find(event => event.start === cellDate);

            // Tyhjennä solun sisältö ennen päivitystä
            cell.innerHTML = `<div>${cellDate}</div>`;
            cell.classList.remove("event-day", "no-event-day");

            if (eventForDate) {
                // Jos tapahtuma on olemassa, merkitään se ja näytetään nimi
                cell.classList.add("event-day");
                cell.innerHTML += `<div class="event-marker">${eventForDate.title}</div>`;
            } else {
                // Jos tapahtumaa ei ole, vain päivämäärä näkyy
                cell.classList.add("no-event-day");
            }
        });
    }

    // Kuuntelee klikkaukset soluihin ja lisää tapahtuman
    calendar.addEventListener("click", async function (event) {
        const target = event.target.closest("td[data-date]");
        if (target) {
            const cellDate = target.getAttribute("data-date");
            const existingEvent = events.find(event => event.start === cellDate);

            if (!existingEvent) {
                const title = prompt("Anna tapahtuman nimi:");
                if (title) {
                    try {
                        const response = await fetch("/api/events", {
                            method: "POST",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify({ title: title, start: cellDate })
                        });
                        if (!response.ok) {
                            throw new Error("Failed to add event");
                        }
                        alert("Tapahtuma lisätty!");
                        await fetchEvents();
                    } catch (error) {
                        console.error("Error adding event:", error);
                        alert("Tapahtuman lisäys epäonnistui: " + error.message);
                    }
                }
            } else {
                alert(`Päivämäärällä ${cellDate} on jo tapahtuma: ${existingEvent.name}`);
            }
        }
    });

    // Alusta tapahtumien haku
    await fetchEvents();

    // Kuukausien vaihtaminen ja kalenterin päivitys
    document.getElementById("prevMonth").addEventListener("click", () => {
        // Päivitä kalenteri taaksepäin ja lataa tapahtumat uudelleen
        // Lisää oma logiikka kuukausien hallintaan
        updateCalendar();
    });

    document.getElementById("nextMonth").addEventListener("click", () => {
        // Päivitä kalenteri eteenpäin ja lataa tapahtumat uudelleen
        // Lisää oma logiikka kuukausien hallintaan
        updateCalendar();
    });
});
