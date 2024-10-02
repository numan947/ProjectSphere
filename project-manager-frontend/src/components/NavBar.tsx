import { Divider, HStack, Image, Spacer, Text } from "@chakra-ui/react";
import icon from "../assets/Icon.svg";
import ColorSwitch from "../building-blocks/ColorSwitch";
import SearchBar from "../building-blocks/SearchBar";
import ProfileButton from "../building-blocks/ProfileButton";

const NavBar = () => {
  return (
    <>
      <HStack minHeight={30} padding={2}>
        <Image src={icon} boxSize="60px" objectFit="cover"></Image>
        <Text
          fontSize={{
            sm: "lg",
            lg: "xl",
            xl: "3xl",
          }}
          as={"b"}
        >
          ProjectSpehere
        </Text>
        <Spacer />
        <SearchBar />
        <Spacer />
        <ProfileButton />
        <ColorSwitch />
      </HStack>
      <Divider />
    </>
  );
};

export default NavBar;
