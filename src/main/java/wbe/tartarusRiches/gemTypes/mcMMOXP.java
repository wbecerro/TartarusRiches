package wbe.tartarusRiches.gemTypes;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class mcMMOXP extends GemType {

    public List<String> skills;

    public mcMMOXP(String id, List<String> skills) {
        super(id);
        this.skills = skills;
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof McMMOPlayerXpGainEvent)) {
            return;
        }

        double power = getGemPowerValue(player, "mcmmoxp");
        if(power <= 0) {
            return;
        }

        for(String skill : skills) {
            if(!((McMMOPlayerXpGainEvent) event).getSkill().equals(PrimarySkillType.valueOf(skill.toUpperCase()))) {
                continue;
            }

            float xp = ((McMMOPlayerXpGainEvent) event).getRawXpGained();
            xp = (float) (xp * power/100 + 1);
            ((McMMOPlayerXpGainEvent) event).setRawXpGained(xp);
            return;
        }
    }
}
