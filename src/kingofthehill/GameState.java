package kingofthehill;

import java.util.EnumSet;
import java.util.Set;

public enum GameState {

	MENU {
		@Override
		public Set<GameState> possibleFollowUps() {
			return EnumSet.of(MENULOADED);
		}
	},
	MENULOADED {
		@Override
		public Set<GameState> possibleFollowUps() {
			return EnumSet.of(LOADGAME);
		}
	},
	LOADGAME {
		public Set<GameState> possibleFollowUps() {
			return EnumSet.of(INGAME);
		}
	},
	INGAME {
		@Override
		public Set<GameState> possibleFollowUps() {
			return EnumSet.of(GAMEOVER, GAMEWIN);
		}
	},
	GAMEOVER {
		@Override
		public Set<GameState> possibleFollowUps() {
			return EnumSet.of(MENU, LOADGAME);
		}
	},
	GAMEWIN {
		@Override
		public Set<GameState> possibleFollowUps() {
			return EnumSet.of(MENU, LOADGAME);
		}
	};
	public Set<GameState> possibleFollowUps() {
		return EnumSet.noneOf(GameState.class);
	}

}