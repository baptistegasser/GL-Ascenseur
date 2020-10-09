package command.model;

/**
 * État courant de l'ascenseur (en arrêt d'urgence, arrêté, en mouvement ...)
 */
public enum State {
    STOPPED,
    EMERGENCY,
    MOVING_UP,
    MOVING_DOWN,
    MOVING_UP_STOP_NEXT,
    MOVING_DOWN_STOP_NEXT
}
