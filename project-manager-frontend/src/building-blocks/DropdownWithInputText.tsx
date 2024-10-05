import {
  Button,
  ChevronDownIcon,
  Select,
  useDisclosure,
} from "@chakra-ui/icons";
import {
  Input,
  InputGroup,
  InputLeftElement,
  InputRightElement,
} from "@chakra-ui/input";
import { Box, Text } from "@chakra-ui/layout";
import { Menu, MenuButton, MenuItem, MenuList } from "@chakra-ui/menu";
import { useRef, useState } from "react";

const DropdownWithInputText = () => {
  const [value, setValue] = useState("");
  const options = ["Option 1", "Option 2", "Option 3"];

  return (
    <InputGroup>
      <InputLeftElement pointerEvents="none">
        {/* Add an icon or other element here if needed */}
      </InputLeftElement>
      <Select
        value={value}
        onChange={(e) => setValue(e.target.value)}
        placeholder="Select an option"
      >
        {options.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </Select>
    </InputGroup>
  );
};

export default DropdownWithInputText;
