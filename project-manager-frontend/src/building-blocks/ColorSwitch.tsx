import { MoonIcon, SunIcon } from "@chakra-ui/icons";
import {
  IconButton,
  Menu,
  MenuButton,
  MenuItem,
  MenuList,
  useColorMode,
} from "@chakra-ui/react";

const ColorSwitch = () => {
  const { colorMode, toggleColorMode } = useColorMode();

  return (
    <Menu>
      <MenuButton
        as={IconButton}
        icon={colorMode === "light" ? <SunIcon /> : <MoonIcon />}
      />
      <MenuList>
        <MenuItem icon={<SunIcon />} onClick={() => toggleColorMode()}>
          Light
        </MenuItem>
        <MenuItem icon={<MoonIcon />} onClick={() => toggleColorMode()}>
          Dark
        </MenuItem>
      </MenuList>
    </Menu>
  );
};

export default ColorSwitch;
