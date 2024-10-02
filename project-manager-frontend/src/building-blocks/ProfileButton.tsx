import { Button, Menu, MenuButton, MenuItem, MenuList } from "@chakra-ui/react";
import { FaSignOutAlt, FaUser } from "react-icons/fa";
import { GrTasks } from "react-icons/gr";

const ProfileButton = () => {
  const userName = "John Doe";
  return (
    <Menu>
      <MenuButton as={Button} variant="ghost">
        {userName}
      </MenuButton>
      <MenuList>
        <MenuItem icon={<FaUser />}>My Account</MenuItem>
        <MenuItem icon={<GrTasks />}>My Issues</MenuItem>
        <MenuItem icon={<FaSignOutAlt />}>Logout</MenuItem>
      </MenuList>
    </Menu>
  );
};

export default ProfileButton;
