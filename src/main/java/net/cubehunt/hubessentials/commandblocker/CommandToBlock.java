package net.cubehunt.hubessentials.commandblocker;

public record CommandToBlock(String command, String permission, String message) {
}