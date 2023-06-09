package com.example.ibuilder.service

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.ibuilder.model.TypeResources

object DialogService {

    fun showDescriptionGame(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Описание игры:")
        builder.setMessage(
            "Вы предводитель поселения, которое избрало путь экономического развития. " +
                    "Вокруг множество кочевников, от которых каждые 10 ходов необходимо откупаться " +
                    "(начиная с 100 хода откупаться придеться каждые 5 ходов). " +
                    "Если не заплатить им дань (100 золота), то они уведут половину жителей деревни. " +
                    "При отсутствии золота и поселенцев - игрок проигрывает." +
                    "\n\nИгра сохраняется автоматически при изменении игровых показателей. " +
                    "Здания, находящиеся на этапе строительства, не сохраняются! Ресурсы не возвращаются!"
        )
        builder.show()
    }

    fun showCostNextEra(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Стоимость смены эпохи:")
        builder.setMessage(
            "1 эпоха:\n" +
                    "Еда: ${EraService.costNextEra["1"]?.get(TypeResources.FOOD)}\n" +
                    "Древесина: ${EraService.costNextEra["1"]?.get(TypeResources.WOOD)}\n\n" +
                    "2 эпоха:\n" +
                    "Еда: ${EraService.costNextEra["2"]?.get(TypeResources.FOOD)}\n" +
                    "Древесина: ${EraService.costNextEra["2"]?.get(TypeResources.WOOD)}\n" +
                    "Золото: ${EraService.costNextEra["2"]?.get(TypeResources.GOLD)}\n\n" +
                    "3 эпоха:\n" +
                    "Еда: ${EraService.costNextEra["3"]?.get(TypeResources.FOOD)}\n" +
                    "Древесина: ${EraService.costNextEra["3"]?.get(TypeResources.WOOD)}\n" +
                    "Золото: ${EraService.costNextEra["3"]?.get(TypeResources.GOLD)}\n" +
                    "Камень: ${EraService.costNextEra["3"]?.get(TypeResources.STONE)}\n"
        )
        builder.show()
    }

    fun showGameOver(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Внимание!")
        builder.setMessage("Игра проиграна! Можете играть дальше, конечно... но лучше начните сначала.")
        builder.show()
    }

    fun showNomadTookGold(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Набег кочевников!")
        builder.setMessage("Вы заплатили дань кочевникам: 100 золотых монет!")
        builder.show()
    }

    fun showNomadTookCitizens(context: Context, citizensSlave: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Набег кочевников!")
        builder.setMessage("Кочевники увели в рабство $citizensSlave жителей!")
        builder.show()
    }
}