package se1.schiffeVersenken.interfaces;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This {@link Annotation} is required to find any {@link PlayerCreator} which are AIs for the BotBattle.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlayableAI {
	
	String value();
}
