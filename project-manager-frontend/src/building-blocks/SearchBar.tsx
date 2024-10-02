import { SearchIcon } from "@chakra-ui/icons";
import {
  HStack,
  Input,
  IconButton,
  InputGroup,
  InputLeftElement,
} from "@chakra-ui/react";

function SearchBar() {
  return (
    <HStack spacing={4} width="60%">
      <InputGroup>
        <InputLeftElement>
          <SearchIcon />
        </InputLeftElement>
        <Input placeholder="Search..." />
      </InputGroup>
      <IconButton
        colorScheme="blue"
        aria-label="Search"
        icon={<SearchIcon />}
      />
    </HStack>
  );
}

export default SearchBar;
