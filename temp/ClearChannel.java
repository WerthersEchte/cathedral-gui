package de.fhkiel.ki.cathedral;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.MessageData;

import java.util.Objects;

import static de.fhkiel.ki.cathedral.Geheim.KLAUS_TOKEN;

public class ClearChannel {

  private static boolean finished;

  public static void main(String[] args) throws InterruptedException {
    GatewayDiscordClient connection = DiscordClient.create(KLAUS_TOKEN)
        .gateway()
        .login()
        .block();

    connection.getEventDispatcher().on(MessageCreateEvent.class)
        .subscribe(
                m -> {
                  for( MessageData d : m.getMessage().getRestChannel().getMessagesBefore(m.getMessage().getId()).buffer().blockFirst()){
                      if(Snowflake.of(d.author().id()).equals(connection.getSelfId())) {
                          connection.getMessageById(Snowflake.of(d.channelId()), Snowflake.of(d.id())).block().delete().block();
                      }
                  }
                  finished = true;
                }
            );
    while(!finished) {
        Thread.sleep(100);
    }
    connection.logout().block();
  }
}
