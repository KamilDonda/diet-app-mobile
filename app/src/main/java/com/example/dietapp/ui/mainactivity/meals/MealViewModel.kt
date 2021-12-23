package com.example.dietapp.ui.mainactivity.meals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.models.Meal

class MealViewModel : ViewModel() {

    val meals = MutableLiveData(ArrayList<Meal>()).apply {
        value = prepareMeals()
    }

    var currentMeal: Meal? = null
        private set

    fun setCurrentMeal(meal: Meal) {
        currentMeal = meal
    }

    fun setCurrentMeal(position: Int) {
        currentMeal = meals.value!![position]
    }

    private fun prepareMeals(): ArrayList<Meal> {
        val meals = ArrayList<Meal>()
        meals.add(
            Meal(
                1,
                "Name1",
                "Do zrobienia zapiekanki potrzebujesz 600 gramów mielonego mięsa. Użyłam szynki wieprzowej, którą zmieliłam w domu" +
                        " na średnich oczkach. Z wieprzowiny polecam też łopatkę, która będzie bardziej tłusta niż szynka.  Dużą cebulę obierz" +
                        " i posiekaj drobno. Posiekaj też obrane pieczarki oraz czosnek. Nagrzej dobrze większą patelnię i dodaj trzy łyżki" +
                        " ulubionego oleju roślinnego do smażenia. Podsmażaj tak warzywa przez 10 minut (bez przykrywki). Mieszaj je co jakiś" +
                        " czas przy pomocy drewnianej łyżki. Na patelnię z podsmażonymi warzywami wyłóż też zmielone mięso oraz przyprawy:" +
                        " łyżeczka słodkiej papryki, pół łyżeczki soli, po 1/4 łyżeczki chili i pieprzu. Zawartość patelni mieszaj przez kilka" +
                        " minut, by dokładnie wymieszać mięso z warzywami. Całość podsmażaj tak przez 10 minut i wyłącz patelnię. Do garnka" +
                        " wlej wodę i zagotuj. Na ugotowanie 400 gramów makaronu potrzebujesz do czterech litrów wody. Wodę posól dopiero," +
                        " gdy zacznie się gotować. Do wrzątku wsyp płaską łyżkę soli. W garnku umieść makaron i gotuj al dente. Ugotowany" +
                        " powinien być sprężysty, ale miękki. Ugotowany makaron przełóż na durszlak. Po ugotowaniu makaron waży u mnie 960" +
                        " gramów. Makaron przełóż do naczynia żaroodpornego, w którym planujesz piec zapiekankę. Naczynie możesz wcześniej" +
                        " wysmarować od środka odrobiną oleju. Na ugotowany makaron wyłóż całą zawartość patelni (mięso mielone z warzywami)." +
                        " Pięć średniej wielkości pomidorów malinowych obierz ze skórki (nie jest to konieczne) i pokrój na mniejsze kawałki" +
                        " i rozłóż na całej powierzchni naczynia. Posiekaj drobno natkę pietruszki i również rozłóż. Większy kawałek ulubionego" +
                        " żółtego sera zetrzyj na tarce, na grubych oczkach i wyłóż na pozostałe składniki. Naczynie z gotową do pieczenia" +
                        " zapiekanką makaronową z mięsem mielonym i warzywami umieść w piekarniku nagrzanym do 185 stopni. Wybierz środkową" +
                        " półkę z opcją pieczenia góra/dół. Zapiekankę piecz bez przykrycia przez 30 minut.",
                "",
                3200f,
                30f,
                64.5f,
                42.536f,
                arrayListOf(
                    "600 g mielonej łopatki wieprzowej",
                    "5 pieczarek",
                    "3 ząbki czosnku",
                    "łyżeczka słodkiej papryki"
                )
            )
        )
        meals.add(Meal(2, "Name2", "", "", 150.21f, 1f, 1f, 1f))
        meals.add(Meal(3, "Name3", "", "", 200.567f, 1f, 1f, 1f))
        meals.add(Meal(4, "Name4", "", "", 32.5f, 1f, 1f, 1f))
        meals.add(Meal(5, "Name5", "", "", 10f, 1f, 1f, 1f))
        meals.add(Meal(6, "Name6", "", "", 15f, 1f, 1f, 1f))

        return meals
    }
}